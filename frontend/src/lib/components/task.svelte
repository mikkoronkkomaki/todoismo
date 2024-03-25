<script lang="ts">
	import { enhance } from '$app/forms';
	import type { Task } from '../../domain/task';

	export let task: Task;
</script>

<div>
	<form data-sveltekit-keepfocus use:enhance method="post" action="?/update">
		<input type="hidden" name="id" value={task.id} />
		<input type="text" class="task-description" name="description" bind:value={task.description} />
		<input type="checkbox" class="done-checkbox form-check-input" name="done" bind:checked={task.done} />
		<button type="submit" class="btn btn-width-small btn-success update-btn">Update</button>
	</form>
	<form use:enhance method="post" action="?/delete">
		<input type="hidden" name="id" value={task.id} />
		<button type="submit" class="btn btn-width-small btn-danger delete-btn">Delete</button>
	</form>
</div>

<style>
	div {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}
	form {
		display: flex;
		justify-content: flex-end;
		gap: 10px;
		align-items: center;
	}
	.task-description {
		width: 36rem;
	}
	.done-checkbox {
		transform: scale(1.5);
	}
	.update-btn {
		margin-left: 1rem;
		width: 5rem;
	}
	.delete-btn {
		width: 5rem;
	}
</style>
