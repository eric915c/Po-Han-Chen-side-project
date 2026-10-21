# CSC317 Group Website

A group e-commerce website developed for a CSC317 course project. The project
includes a product-focused frontend and a Node.js/Express backend connected to
MySQL.

## Features

- Product browsing and product detail pages
- Product search and filtering interactions
- Account registration and login flows
- Password hashing with `bcryptjs`
- Express routes and MySQL database access
- Shopping cart and account-related pages

## My contribution

As a group project, the work was shared among team members. I contributed to
the HTML/CSS design and gained hands-on exposure to Node.js, Express, MySQL,
bcrypt-based password handling, registration and login flows, product pages,
and search functionality. I did not independently implement every backend or
database feature.

## Development Challenge: MySQL Connection

During development, our team encountered MySQL connection errors, including
access and authentication errors. We checked the database user's permissions,
verified that the user could access the project database, and made sure that
the environment variables in the `.env` file matched the database settings.
After updating the permissions and configuration, we tested the registration
and login flows again to confirm that the application could connect to the
database successfully.

We used ChatGPT as a troubleshooting aid, but the team tested the suggested
changes and checked the results in the application.

## Technology

- HTML, CSS, and JavaScript
- Node.js and Express
- MySQL / mysql2
- bcryptjs

## Run locally

Install the dependencies:

```bash
npm install
```

Create a `.env` file based on `.env.example`, configure a local MySQL database,
and then start the server:

```bash
node server317.js
```

Open <http://localhost:3000> in a browser.

The project was originally completed as a course group assignment.
