# REDJR Marketplace — Portfolio Demo

REDJR is a marketplace web application developed as a team project for a
university software engineering course. Originally, our team ran the
application on a server and MySQL database that we managed ourselves. Since
the original deployment is no longer active, this portfolio version uses a
local SQLite database with sample data. This allows the main features to be
viewed and tested without the original server.

## Project Features

- Marketplace search and category filtering
- Product listing and product detail pages
- User registration and login
- Seller dashboard and messaging features
- Product image display
- Responsive HTML/CSS templates

## My contribution

This was a team project. My primary contribution was frontend development,
including marketplace page layouts, HTML templates, and CSS styling. My work
focused on the frontend presentation and user interface. Backend and database
features were handled by other team members; I did not independently implement
those parts of the system.

## Run locally

From this folder, create and activate a virtual environment, then install the
dependencies:

```cmd
py -m venv .venv
.venv\Scripts\activate
pip install -r requirements.txt
python run.py
```

Open <http://127.0.0.1:5000> in a browser. The first startup creates
`redjr_demo.db` locally and inserts sample categories, listings, and a demo
user. The database file is ignored by Git.

Demo login:

- Email: `demo@sfsu.edu`
- Password: `DemoPass123!`

The original server credentials and remote database configuration are not
included in this portfolio version.
