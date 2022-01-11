ALTER TABLE web_page_template
ALTER COLUMN publish_date DROP NOT NULL;

ALTER TABLE web_page_template_history
ALTER COLUMN publish_date DROP NOT NULL;

ALTER TABLE web_page
ALTER COLUMN publish_date DROP NOT NULL;

ALTER TABLE web_page_history
ALTER COLUMN publish_date DROP NOT NULL;

ALTER TABLE media
ALTER COLUMN publish_date DROP NOT NULL;

ALTER TABLE media_history
ALTER COLUMN publish_date DROP NOT NULL;

