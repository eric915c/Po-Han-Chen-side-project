from datetime import datetime
from pathlib import Path


def initialize_demo_data(db):
    """Create a small, local-only dataset for the portfolio demonstration."""
    from models import Categories, ItemImages, Items, User

    db.create_all()

    if Categories.query.first():
        return

    categories = [
        Categories(category_name="Furniture"),
        Categories(category_name="Electronics"),
        Categories(category_name="Books"),
    ]
    db.session.add_all(categories)
    db.session.flush()

    demo_user = User(name="Demo Seller", email="demo@sfsu.edu")
    demo_user.set_password("DemoPass123!")
    db.session.add(demo_user)
    db.session.flush()

    demo_items = [
        Items(
            title="Desk Lamp",
            category_id=categories[0].category_id,
            item_description="A sample listing used for the local portfolio demo.",
            item_condition="Used - Like New",
            price=18.00,
            seller_id=demo_user.id,
            is_approved=True,
            created_at=datetime.utcnow(),
        ),
        Items(
            title="Wireless Keyboard",
            category_id=categories[1].category_id,
            item_description="A second sample listing for testing search and item details.",
            item_condition="Used - Good",
            price=25.00,
            seller_id=demo_user.id,
            is_approved=True,
            created_at=datetime.utcnow(),
        ),
    ]
    db.session.add_all(demo_items)
    db.session.flush()

    image_path = Path(__file__).resolve().parent / "static" / "images" / "logo1.png"
    if image_path.exists():
        image_data = image_path.read_bytes()
        for item in demo_items:
            db.session.add(
                ItemImages(
                    item_id=item.item_id,
                    image_data=image_data,
                    mime_type="image/png",
                )
            )

    db.session.commit()
