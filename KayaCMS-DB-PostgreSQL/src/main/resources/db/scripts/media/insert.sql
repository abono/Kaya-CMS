insert into media (
  type,
  path,
  content,
  type_edits,
  path_edits,
  content_edits,
  create_date,
  modify_date,
  publish_date,
  web_site_id
) values (
  '',
  '',
  '',
  :typeEdits,
  :pathEdits,
  :contentEdits,
  now(),
  now(),
  null,
  :webSiteId
)
