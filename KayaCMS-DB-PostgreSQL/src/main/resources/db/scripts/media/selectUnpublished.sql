select
    media_id,
    type,
    path,
    type_edits,
    path_edits,
    create_date,
    modify_date,
    publish_date,
    web_site_id
from media
where
    web_site_id = :webSiteId
    and (
        not type_edits = ''
        or not path_edits = ''
        or not length(content_edits) > 0
    )
