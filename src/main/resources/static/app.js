let currentPos = "STANDING";
let activePlayerId = null;

/**
 * Sends request to PlayerController to save a new fighter to PostgreSQL
 */
async function createFighter() {
    const name = document.getElementById('playerName').value;
    const style = document.getElementById('playerStyle').value;
    const log = document.getElementById('setup-log');

    // POST request to create the player in the database
    const response = await fetch(`/api/v1/players/create?name=${name}&style=${style}`, {
        method: 'POST'
    });

    if (response.ok) {
        const player = await response.json();

        // Store the ID globally so executeMove can use it
        activePlayerId = player.id;

        log.innerText = `Character Created! ID: ${player.id} | Name: ${player.name}`;
    } else {
        log.innerText = "Error creating character. Check console.";
    }
}

/**
 * Sends request to MatchController to calculate combat outcome
 */
async function executeMove(moveName) {
    const logElement = document.getElementById('log');

    // Prepare the JSON body for the request
    const requestData = {
        attacker: "BJJ",
        defender: "WRESTLER",
        move: moveName,
        currentPosition: currentPos
    };

    try {
        // We append the playerId as a query parameter if it exists
        const url = activePlayerId
            ? `/api/v1/match/resolve?playerId=${activePlayerId}`
            : '/api/v1/match/resolve';

        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) throw new Error('Server Error (Check backend logs)');

        const result = await response.json();

        // Update the scrolling combat log
        logElement.innerHTML += `<div>> ${result.actionLog}</div>`;
        logElement.scrollTop = logElement.scrollHeight;

        // If the move worked, update the position state
        if (result.success) {
            currentPos = result.newPosition;
            document.getElementById('pos-display').innerText = currentPos;
        }

        // If the match ended, show the final message
        if (result.isGameOver) {
            logElement.innerHTML += `<div style="color: gold;">*** MATCH OVER - GAINED ${result.pointsGained} XP! ***</div>`;
            currentPos = "STANDING";
            document.getElementById('pos-display').innerText = currentPos;
        }

    } catch (error) {
        logElement.innerHTML += `<div style="color: #e74c3c;">! Error: ${error.message}</div>`;
    }
}