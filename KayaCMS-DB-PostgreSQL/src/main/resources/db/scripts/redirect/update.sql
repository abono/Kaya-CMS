update web_site
set
  from_path = :fromPath,
  to_path = :toPath,
  modify_date = now(),
  web_site_id = :webSiteId
where redirect_id = :redirectId
