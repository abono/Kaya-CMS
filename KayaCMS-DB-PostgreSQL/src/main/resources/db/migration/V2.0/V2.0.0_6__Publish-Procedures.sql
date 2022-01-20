ALTER TABLE WEB_PAGE_TEMPLATE_HISTORY
ADD COLUMN WEB_SITE_ID INT;

UPDATE WEB_PAGE_TEMPLATE_HISTORY
SET WEB_SITE_ID = (
  SELECT WEB_SITE_ID
  FROM WEB_PAGE_TEMPLATE
  WHERE WEB_PAGE_TEMPLATE.WEB_PAGE_TEMPLATE_ID = WEB_PAGE_TEMPLATE_HISTORY.WEB_PAGE_TEMPLATE_ID
);

ALTER TABLE WEB_PAGE_TEMPLATE_HISTORY
ALTER COLUMN WEB_SITE_ID SET NOT NULL;


ALTER TABLE WEB_PAGE_HISTORY
ADD COLUMN WEB_SITE_ID INT;

UPDATE WEB_PAGE_HISTORY
SET WEB_SITE_ID = (
  SELECT WEB_SITE_ID
  FROM WEB_PAGE
  WHERE WEB_PAGE.WEB_PAGE_ID = WEB_PAGE_HISTORY.WEB_PAGE_ID
);

ALTER TABLE WEB_PAGE_TEMPLATE_HISTORY
ALTER COLUMN WEB_SITE_ID SET NOT NULL;


create or replace procedure publish_web_page_template(id int)
language plpgsql
as $$
declare
  -- variable declaration
begin
  -- Copy to history table
  insert into web_page_template_history (
	  name,
	  content,
	  create_date,
	  modify_date,
	  publish_date,
	  web_site_id,
	  web_page_template_id
	)
	select
	  name,
	  content,
	  create_date,
	  modify_date,
	  publish_date,
	  web_site_id,
	  web_page_template_id
	from web_page_template
	where web_page_template_id = id
	;
	
	-- Move content to live and mark published
	update web_page_template
	set
	  name = name_edits,
	  content = content_edits,
	  modify_date = now(),
    publish_date = now()
  where web_page_template_id = id
  ;
  
  update web_page_template
  set
    name_edits = '',
    content_edits = ''
  where web_page_template_id = id
  ;
end; $$
;

create or replace procedure publish_web_page(id int)
language plpgsql
as $$
declare
  -- variable declaration
begin
  -- Copy to history table
  insert into web_page_history (
    type,
	  path,
	  title,
	  description,
	  content,
	  parameters,
	  create_date,
	  modify_date,
	  publish_date,
    web_site_id,
	  web_page_template_id,
	  web_page_id
  )
  select
    type,
	  path,
	  title,
	  description,
	  content,
	  parameters,
	  create_date,
	  modify_date,
	  publish_date,
    web_site_id,
	  web_page_template_id,
	  web_page_id
  from web_page
  where web_page_id = id
  ;
  
  -- Move content to live and mark published
  update web_page
  set
    type = type_edits,
    path = path_edits,
    title = title_edits,
    description = description_edits,
    content = content_edits,
    parameters = parameters_edits,
    modify_date = now(),
    publish_date = now(),
    web_page_template_id = web_page_template_id_edits
  where web_page_id = id
  ;
  
  update web_page
  set
    type_edits = '',
    path_edits = '',
    title_edits = '',
    description_edits = '',
    content_edits = '',
    parameters_edits = ''
  where web_page_id = id
  ;
end; $$
;

create or replace procedure publish_media(id int)
language plpgsql
as $$
declare
  -- variable declaration
begin
  -- Copy to history table
  insert into media_history (
    type,
    path,
    content,
    create_date,
    modify_date,
    publish_date,
    web_site_id
  )
  select
    type,
    path,
    content,
    create_date,
    modify_date,
    publish_date,
    web_site_id
  from media
  where media_id = id
  ;
  
  -- Move content to live and mark published
  update media
  set
    type = type_edits,
    path = path_edits,
    content = content_edits,
    modify_date = now(),
    publish_date = now()
  where media_id = id
  ;
  
  update media
  set
    type_edits = '',
    path_edits = '',
    content_edits = ''
  where media_id = id
  ;
end; $$
;
