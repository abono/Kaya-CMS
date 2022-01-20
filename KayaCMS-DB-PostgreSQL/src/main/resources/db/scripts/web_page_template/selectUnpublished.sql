select
    web_page_template_id,
    name,
    name_edits,
    create_date,
    modify_date,
    publish_date,
    web_site_id
from web_page
where
    web_site_id = :webSiteId
    and (
        not name_edits = ''
        or not content_edits = ''
    )
