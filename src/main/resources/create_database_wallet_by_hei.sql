SELECT 'CREATE DATABASE wallet_by_hei'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'wallet_by_hei')\gexec

\c wallet_by_hei ;