insert into admin_user (
  first_name,
  last_name,
  email,
  user_name,
  password,
  create_date,
  modify_date,
  web_site_id
) values (
  :firstName,
  :lastName,
  :email,
  :userName,
  :password,
  now(),
  now(),
  :webSiteId
)
