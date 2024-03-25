<script lang="ts">
	import type { Task } from '../../domain/task';
	import TaskItem from './task.svelte';

	export let tasks: Task[] = [];
</script>

<ul>
	{#each tasks as task (task.id)}
		<TaskItem {task} />
	{:else}
		<p>No items found</p>
	{/each}
</ul>

<style>
	ul {
		margin: 0;
		padding: 0;
		display: flex;
		flex-direction: column;
		gap: 0.25rem;
	}
</style>
