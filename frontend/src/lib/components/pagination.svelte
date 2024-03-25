<script lang="ts">
    export let page: number;
    export let remaining: number;
    export let urlFn: (page: number) => string;
</script>

<div class="d-flex justify-content-center">
    {#if page > 0}
        <a class="btn btn-primary btn-width btn-margin" href={urlFn(page - 1)}>&#9664; Previous page</a>
    {/if}
    {#if remaining > 0}
        <a class="btn btn-primary btn-width btn-margin" href={urlFn(page + 1)}>Next page &#9654;</a>
    {/if}
</div>