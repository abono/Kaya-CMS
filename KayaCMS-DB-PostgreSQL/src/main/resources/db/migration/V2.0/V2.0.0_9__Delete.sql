CREATE OR REPLACE FUNCTION web_page_template_delete_fn() RETURNS TRIGGER AS
$$BEGIN
  INSERT INTO web_page_template_history (
    name,
    content,
    create_date,
    modify_date,
    publish_date,
    web_site_id,
    web_page_template_id
  )
  SELECT
    name,
    content,
    now(),
    modify_date,
    publish_date,
    web_site_id,
    web_page_template_id
  FROM web_page_template
  WHERE web_page_template_id = OLD.web_page_template_id
  ;
  
  return OLD;
END;$$
LANGUAGE plpgsql;

CREATE TRIGGER web_page_template_delete_tr
BEFORE DELETE ON web_page_template
FOR EACH ROW
EXECUTE PROCEDURE web_page_template_delete_fn();


CREATE OR REPLACE FUNCTION web_page_delete_fn() RETURNS TRIGGER AS
$$BEGIN
  INSERT INTO web_page_history (
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
  SELECT
    type,
    path,
    title,
    description,
    content,
    parameters,
    now(),
    modify_date,
    publish_date,
    web_site_id,
    web_page_template_id,
    web_page_id
  FROM web_page
  WHERE web_page_id = OLD.web_page_id
  ;
  
  return OLD;
END;$$
LANGUAGE plpgsql;

CREATE TRIGGER web_page_delete_tr
BEFORE DELETE ON web_page
FOR EACH ROW
EXECUTE PROCEDURE web_page_delete_fn();


CREATE OR REPLACE FUNCTION media_delete_fn() RETURNS TRIGGER AS
$$BEGIN
  INSERT INTO media_history (
    type,
    path,
    content,
    create_date,
    modify_date,
    publish_date,
    web_site_id,
    media_id
  )
  SELECT
    type,
    path,
    content,
    now(),
    modify_date,
    publish_date,
    web_site_id,
    media_id
  FROM media
  WHERE media_id = OLD.media_id
  ;
  
  return OLD;
END;$$
LANGUAGE plpgsql;

CREATE TRIGGER media_delete_tr
BEFORE DELETE ON media
FOR EACH ROW
EXECUTE PROCEDURE media_delete_fn();



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
    now(),
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
    now(),
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
    web_site_id,
    media_id
  )
  select
    type,
    path,
    content,
    now(),
    modify_date,
    publish_date,
    web_site_id,
    media_id
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


