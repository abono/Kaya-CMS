select
  web_page_template_id,
  name,
  content,
  name_edits,
  content_edits,
  create_date,
  modify_date,
  publish_date,
  web_site_id
from web_page_template
where
  web_site_id = :webSiteId
  and (
    not name_edits = ''
    or not content_edits = ''
  )
