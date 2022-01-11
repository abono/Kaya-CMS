import { APIGet, APIPost, APIPut, APIDelete } from './ServiceUtil';

export const SearchAdminUser = (page, itemsPerPage, dispatch) => {
    console.log("Searching admin user", page, itemsPerPage);
    APIGet('/api/admin/adminUser?page=' + page + '&itemsPerPage=' + itemsPerPage, dispatch);
}

export const GetAdminUser = (id, dispatch) => {
    console.log("Getting admin user", id);
    APIGet('/api/admin/adminUser/' + id, dispatch);
}

export const CreateAdminUser = (adminUser, dispatch) => {
    console.log("Creating admin user", adminUser);
    const body = {
      firstName: adminUser.firstName,
      lastName: adminUser.lastName,
      email: adminUser.email,
      userName: adminUser.userName,
      password: adminUser.password
    };
    APIPost('/api/admin/adminUser', body, dispatch);
}

export const UpdateAdminUser = (adminUser, dispatch) => {
    console.log("Updating admin user", adminUser);
    const body = {
      firstName: adminUser.firstName,
      lastName: adminUser.lastName,
      email: adminUser.email,
      userName: adminUser.userName,
      password: adminUser.password
    };
    APIPut('/api/admin/adminUser/' + adminUser.adminUserId, body, dispatch);
}

export const DeleteAdminUser = (id, dispatch) => {
    console.log("Delete admin user", id);
    APIDelete('/api/admin/adminUser/' + id, dispatch);
}
