insert into web_site (
  domain_name,
  set_up_complete,
  create_date,
  modify_date
) values (
  :domainName,
  :setUpComplete,
  now(),
  now()
)
