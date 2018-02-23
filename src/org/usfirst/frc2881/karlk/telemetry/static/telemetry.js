'use strict'

function setMode({data}) {
    const {mode, enabled} = JSON.parse(data)
    console.log('mode', mode, enabled)
}

function setHeaders({data}) {
    const headers = JSON.parse(data)
    console.log('headers', headers)
}

function addData({data}) {
    const values = JSON.parse(data)
    console.log('data', values)
}

let source = new EventSource('telemetry')
source.addEventListener('mode', setMode)
source.addEventListener('headers', setHeaders)
source.addEventListener('data', addData)
