import os

from flask import Flask
from config import Config
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.secret_key = os.getenv("SECRET_KEY", "redjr-local-demo-key")
app.config.from_object(Config)
db = SQLAlchemy(app)

from routes import *
from demo_data import initialize_demo_data

register_blueprints(app)

if app.config["DEMO_MODE"]:
    with app.app_context():
        initialize_demo_data(db)


