alter table web_site
add column name varchar(50);

update web_site
set name = domain_name;

alter table web_site
alter column name set not null;
