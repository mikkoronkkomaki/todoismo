<script lang="ts">
    import TaskForm from "../new-task.svelte";
    import Search from "../search.svelte";
    import SearchResults from "../search-results.svelte";
    import RecentTasks from "../recent-tasks.svelte";
    import Pagination from "../pagination.svelte";
    import type {PageData} from "./$types";

    export let data: PageData

    $: ({currentPage, size, query, foundTasks, remainingTasks} = data);
    $: console.log(data);
</script>

<style>
    .content {
        width: 50rem;
        margin: 0 auto;
        white-space: nowrap;
    }

    .display-1 {
        text-align: center;
    }
</style>


<div class="d-flex justify-content-center align-items-center">
    <div class="content">
        <h1 class="display-1">TODOISMO!</h1>
        <div class="my-sm-4">
            <TaskForm></TaskForm>
        </div>
        <div class="my-sm-4">
            <RecentTasks></RecentTasks>
        </div>
        <div class="my-sm-4">
            <Search {query}></Search>
            <SearchResults {foundTasks}></SearchResults>
            <Pagination {currentPage} {remainingTasks}></Pagination>
        </div>
    </div>
</div>
