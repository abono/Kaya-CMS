insert into web_site (
  from_path,
  to_path,
  create_date,
  modify_date,
  web_site_id
) values (
  :fromPath,
  :toPath,
  now(),
  now(),
  :webSiteId
)
