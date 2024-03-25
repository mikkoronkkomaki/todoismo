<script lang="ts">
    import Search from '../lib/components/search.svelte';
    import TaskList from '../lib/components/task-list.svelte';
    import Pagination from '../lib/components/pagination.svelte';
    import NewTask from '../lib/components/new-task.svelte';
    import {page} from '$app/stores';

    import type {PageData} from './$types';

    export let data: PageData;

    const paginationUrl = (p: number) => {
        const url = new URL($page.url);
        url.searchParams.set('page', p.toString());
        url.searchParams.set('query', data.query || '');
        return url.toString();
    };
</script>

<main>
    <div class="d-flex justify-content-center align-items-center">
        <div class="content">
            <h1 class="display-1">TODOISMO!</h1>

            <div class="my-sm-4">
                <NewTask/>
            </div>

            <div class="my-sm-4">
                <Search/>
            </div>

            <div class="my-sm-4">
                <TaskList tasks={data.tasks}/>
            </div>

            <div class="my-sm-4">
                <Pagination page={data.page} remaining={data.remaining} urlFn={paginationUrl}/>
            </div>
        </div>
    </div>
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    .content {
        width: 50rem;
        margin: 0 auto;
        white-space: nowrap;
    }

    .display-1 {
        text-align: center;
    }
</style>
