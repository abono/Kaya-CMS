update admin_user
set
  first_name = :firstName,
  last_name = :lastName,
  email = :email,
  user_name = :userName,
  password = :password,
  modify_date = now()
where admin_user_id = :adminUserId
