update web_site
set
  domain_name = :domainName,
  set_up_complete = :setUpComplete,
  modify_date = now()
where web_site_id = :webSiteId
