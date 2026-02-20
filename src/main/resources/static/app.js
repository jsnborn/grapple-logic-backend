let currentPos = "STANDING";

/**
 * Sends request to PlayerController to save a new fighter to PostgreSQL
 */
async function createFighter() {
    const name = document.getElementById('playerName').value;
    const style = document.getElementById('playerStyle').value;
    const log = document.getElementById('setup-log');

    const response = await fetch(`/api/v1/players/create?name=${name}&style=${style}`, {
        method: 'POST'
    });

    if (response.ok) {
        const player = await response.json();
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

    const requestData = {
        attacker: "BJJ",
        defender: "WRESTLER",
        move: moveName,
        currentPosition: currentPos
    };

    try {
        const response = await fetch('/api/v1/match/resolve', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) throw new Error('Server Error (Check your Enum names!)');

        const result = await response.json();

        logElement.innerHTML += `<div>> ${result.actionLog}</div>`;
        logElement.scrollTop = logElement.scrollHeight;

        if (result.success) {
            currentPos = result.newPosition;
            document.getElementById('pos-display').innerText = currentPos;
        }

        if (result.isGameOver) {
            logElement.innerHTML += `<div style="color: gold;">*** MATCH OVER ***</div>`;
        }

    } catch (error) {
        logElement.innerHTML += `<div style="color: #e74c3c;">! Error: ${error.message}</div>`;
    }
}