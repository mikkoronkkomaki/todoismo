<script>
    import {onMount} from 'svelte';
    import {recentTasksList} from './stores';

    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    let description = '';

    const setNewRecentTask = (newTask) => {
        recentTasksList.update(tasks => {
            if (tasks.length >= 3) {
                tasks.shift();
            }
            return [...tasks, newTask];
        });
    };

    async function submitForm(event) {
        event.preventDefault();
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

<style>
    .flex-grow {
        flex-grow: 1;
    }

    .no-wrap {
        white-space: nowrap;
    }

    .btn-width {
        width: 200px;
    }
</style>

<h5>Add a new task</h5>

<form on:submit={submitForm}>
    <div class="d-flex justify-content-between">
        <input id="description" bind:value={description} required class="form-control flex-grow"
               placeholder="Task description">
        <button type="submit" class="btn btn-primary ms-2 no-wrap btn-width">Add Task</button>
    </div>
</form>