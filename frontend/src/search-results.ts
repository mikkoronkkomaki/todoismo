import type {Task} from "./task";

export interface SearchResults {
    tasks: Task[];
    remaining: number;
}