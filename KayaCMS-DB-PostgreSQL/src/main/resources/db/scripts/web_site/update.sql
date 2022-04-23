update web_site
set
  name = :name,
  domain_name = :domainName,
  set_up_complete = :setUpComplete,
  modify_date = now()
where web_site_id = :webSiteId
