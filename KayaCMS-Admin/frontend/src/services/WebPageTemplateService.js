import { APIGet, APIPost, APIPut, APIDelete } from './ServiceUtil';

export const SearchWebPageTemplate = (page, itemsPerPage, dispatch) => {
    console.log("Searching web page template", page, itemsPerPage);
    APIGet('/api/admin/webPageTemplate?page=' + page + '&itemsPerPage=' + itemsPerPage, dispatch);
}

export const GetWebPageTemplate = (id, dispatch) => {
    console.log("Getting web page template", id);
    APIGet('/api/admin/webPageTemplate/' + id, dispatch);
}

export const CreateWebPageTemplate = (webPageTemplate, dispatch) => {
    console.log("Creating web page template", webPageTemplate);
    const body = {
      nameEdits: webPageTemplate.nameEdits,
      contentEdits: webPageTemplate.contentEdits
    };
    APIPost('/api/admin/webPageTemplate', body, dispatch);
}

export const UpdateWebPageTemplate = (webPageTemplate, dispatch) => {
    console.log("Updating web page template", webPageTemplate);
    const body = {
      nameEdits: webPageTemplate.nameEdits,
      contentEdits: webPageTemplate.contentEdits
    };
    APIPut('/api/admin/webPageTemplate/' + webPageTemplate.webPageTemplateId, body, dispatch);
}

export const DeleteWebPageTemplate = (id, dispatch) => {
    console.log("Delete web page template", id);
    APIDelete('/api/admin/webPageTemplate/' + id, dispatch);
}
