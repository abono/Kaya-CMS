insert into web_page_template (
  name,
  content,
  name_edits,
  content_edits,
  create_date,
  modify_date,
  publish_date,
  web_site_id
) values (
  '',
  '',
  :nameEdits,
  :contentEdits,
  now(),
  now(),
  null,
  :webSiteId
)
