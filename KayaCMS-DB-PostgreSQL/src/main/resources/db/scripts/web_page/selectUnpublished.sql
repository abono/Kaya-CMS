select
  web_page_id,
  type,
  path,
  title,
  description,
  content,
  parameters,
  type_edits,
  path_edits,
  title_edits,
  description_edits,
  content_edits,
  parameters_edits,
  create_date,
  modify_date,
  publish_date,
  web_site_id,
  web_page_template_id,
  web_page_template_id_edits
from web_page
where
  web_site_id = :webSiteId
  and (
    not type_edits = ''
    or not path_edits = ''
    or not title_edits = ''
    or not description_edits = ''
    or not content_edits = ''
    or not parameters_edits = ''
    or not web_page_template_id = web_page_template_id_edits
  )
