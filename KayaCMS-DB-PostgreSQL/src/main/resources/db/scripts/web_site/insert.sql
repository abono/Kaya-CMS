insert into web_site (
  name,
  domain_name,
  set_up_complete,
  create_date,
  modify_date
) values (
  :name,
  :domainName,
  :setUpComplete,
  now(),
  now()
)
