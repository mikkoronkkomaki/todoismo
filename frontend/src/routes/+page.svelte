<script>
    import TaskForm from "../TaskForm.svelte";
    import Search from "../Search.svelte";
</script>

<div class="d-flex justify-content-center align-items-center">
    <div>
        <h1 class="display-1">TODOISMO!</h1>
        <div class="my-sm-4">
            <TaskForm></TaskForm>
        </div>
        <div class="my-sm-4">
            <Search></Search>
        </div>
    </div>
</div>