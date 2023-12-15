import axios from "axios";

function post(url, data, success, error, failure) {
    axios.post(url, data, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.status, data.name)
        else
            failure(data.message, data.status)
    }).catch(error)
}

function get(url, success, failure, error) {
    axios.get(url, {
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.status)
        else
            failure(data.message, data.status)
    }).catch(error)
}

export {get, post}