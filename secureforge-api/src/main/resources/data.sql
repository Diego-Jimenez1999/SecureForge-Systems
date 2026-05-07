INSERT INTO free_resources (title, description, download_url)
SELECT 'Checklist OWASP Básico', 'Guía rápida de revisión de seguridad para apps web.', 'https://owasp.org/'
WHERE NOT EXISTS (SELECT 1 FROM free_resources WHERE title = 'Checklist OWASP Básico');
