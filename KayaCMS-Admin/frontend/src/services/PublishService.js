import { APIGet } from './ServiceUtil';

export const GetUnpublishedEntities = (dispatch) => {
    console.log("Getting unpublished entities");
    APIGet('/api/admin/publish', dispatch);
}
