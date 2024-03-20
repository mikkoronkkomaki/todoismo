<script lang="ts">
    import {recentTasksList} from './stores';
    import type {Task}  from "./task";

    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    let description = '';

    const setNewRecentTask = (newTask: Task) => {
        recentTasksList.update(tasks => {
            return [...tasks.slice(tasks.length >= 3 ? 1 : 0), newTask]
        });
    }

    async function saveTask() {
        const response = await fetch(`${API_BASE_URL}/api/tasks`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({description})
        });
        if (response.ok) {
            const responseBody = await response.json();
            setNewRecentTask(responseBody);
            description = '';
        } else {
            console.error('Failed to add task');
        }
    }
</script>

<h5>Add a new task</h5>

<form on:submit|preventDefault={saveTask}>
    <div class="d-flex justify-content-between">
        <input id="description" bind:value={description} required class="form-control flex-grow"
               placeholder="Task description">
        <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Add Task</button>
    </div>
</form>