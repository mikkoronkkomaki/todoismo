<script>
    import TaskForm from "../TaskForm.svelte";
    import Search from "../Search.svelte";
    import RecentTasks from "../RecentTasks.svelte";
</script>

<style>
    .content {
        width: 800px;
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
            <Search></Search>
        </div>
    </div>
</div>
