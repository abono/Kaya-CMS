insert into web_page (
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
) values (
  '',
  '',
  '',
  '',
  '',
  '',
  :typeEdits,
  :pathEdits,
  :titleEdits,
  :descriptionEdits,
  :contentEdits,
  :parametersEdits,
  now(),
  now(),
  null,
  :webSiteId,
  :webPageTemplateIdEdits,
  :webPageTemplateIdEdits
)