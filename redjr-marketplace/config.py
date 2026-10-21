import os
from pathlib import Path


BASE_DIR = Path(__file__).resolve().parent
DEFAULT_DATABASE_URI = f"sqlite:///{(BASE_DIR / 'redjr_demo.db').as_posix()}"


class Config:
    # The portfolio copy uses a local SQLite database by default. A different
    # database can be supplied explicitly for development with DATABASE_URL.
    SQLALCHEMY_DATABASE_URI = os.getenv("DATABASE_URL", DEFAULT_DATABASE_URI)
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    DEMO_MODE = os.getenv("DEMO_MODE", "true").lower() == "true"


