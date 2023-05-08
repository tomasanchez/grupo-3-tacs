import { EventRequest, VoteRequest } from "../models/dataApi";

const url = import.meta.env.VITE_URL


export async function addEvent(urlInput: string, { arg }: { arg: EventRequest }) {

    const requestOptions = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(arg)
    };

    return fetch(url + urlInput, requestOptions).then(res => res.json())
}

export async function joinEvent(id: string, username: string) {

    const requestOptions = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ "username": username })
    };

    return fetch(url + `scheduler-service/schedules/${id}/relationships/guests`, requestOptions).then(res => res.json())
}

export async function voteOption(id: string, vote: VoteRequest) {

    const requestOptions = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(vote)
    };

    return fetch(url + `scheduler-service/schedules/${id}/options`, requestOptions).then(res => res.json())
}


export async function toggleVoting(id: string, username: string, voting: boolean) {

    const requestOptions = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ "username": username, "voting": voting })
    };

    return fetch(url + `scheduler-service/schedules/${id}/voting`, requestOptions).then(res => res.json())
}