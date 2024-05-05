import request from '../../utils/request'

export function mergeFile(data: any) {
    return request({
        url: "uploader/merge?url=" + data,
        method: "post"
    })
}



