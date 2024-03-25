import {fail} from '@sveltejs/kit';

let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

interface RequestData {
    formData: () => Promise<FormData>;
}

interface ActionParams {
    request: RequestData;
}

function sleep() {
    // Stupid hack to wait that the changes have been propagated to ElasticSearch
    let ms = 1000;
    return new Promise(resolve => setTimeout(resolve, ms));
}
export const actions = {
    create: async ({request} : ActionParams) => {
        const description = (await request.formData()).get('description')?.toString();
        if (description === undefined) return fail(400, {description, missing: true});

        const response = await fetch(`${API_BASE_URL}/api/tasks`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({description})
        });
        if (response.ok) {
            const responseBody = await response.json();
            await sleep();
            return {success: true};
        } else {
            return fail(400, {error: 'something fucky'});
        }
    },
    update: async ({request} : ActionParams) => {
        const fd = await request.formData();
        const id = fd.get('id')?.toString();
        const description = fd.get('description')?.toString();
        const done = fd.get('done')?.toString() === 'on';

        const response = await fetch(`${API_BASE_URL}/api/tasks/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                description: description,
                done: done
            })
        });
        if (response.ok) {
            await sleep();
            return {success: true};
        } else {
            return fail(400, {error: 'something fucky'});
        }
    },
    delete: async ({request} : ActionParams) => {
        const id = (await request.formData()).get('id')?.toString();
        if (id === undefined) return fail(400, {id, missing: true});

        const response = await fetch(`${API_BASE_URL}/api/tasks/${id}`, {
            method: 'DELETE',
        });
        if (response.ok) {
            await sleep();
            return {success: true};
        } else {
            return fail(400, {error: 'something fucky'});
        }
    }
};
