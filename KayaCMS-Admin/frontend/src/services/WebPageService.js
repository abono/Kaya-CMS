import { APIGet, APIPost, APIPut, APIDelete } from './ServiceUtil';

export const SearchWebPage = (page, itemsPerPage, dispatch) => {
    console.log("Searching web page template", page, itemsPerPage);
    APIGet('/api/admin/webPage?page=' + page + '&itemsPerPage=' + itemsPerPage, dispatch);
}

export const GetWebPage = (id, dispatch) => {
    console.log("Getting web page template", id);
    APIGet('/api/admin/webPage/' + id, dispatch);
}

export const CreateWebPage = (webPage, dispatch) => {
    console.log("Creating web page template", webPage);
    const body = {
      webPageTemplateIdEdits: webPage.webPageTemplateIdEdits,
      typeEdits: webPage.typeEdits,
      pathEdits: webPage.pathEdits,
      titleEdits: webPage.titleEdits,
      descriptionEdits: webPage.descriptionEdits,
      contentEdits: webPage.contentEdits,
      parametersEdits: webPage.parametersEdits
    };
    APIPost('/api/admin/webPage', body, dispatch);
}

export const UpdateWebPage = (webPage, dispatch) => {
    console.log("Updating web page template", webPage);
    const body = {
      webPageTemplateIdEdits: webPage.webPageTemplateIdEdits,
      typeEdits: webPage.typeEdits,
      pathEdits: webPage.pathEdits,
      titleEdits: webPage.titleEdits,
      descriptionEdits: webPage.descriptionEdits,
      contentEdits: webPage.contentEdits,
      parametersEdits: webPage.parametersEdits
    };
    APIPut('/api/admin/webPage/' + webPage.webPageId, body, dispatch);
}

export const DeleteWebPage = (id, dispatch) => {
    console.log("Delete web page template", id);
    APIDelete('/api/admin/webPage/' + id, dispatch);
}
