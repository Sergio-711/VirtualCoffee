import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from fastapi.testclient import TestClient
from app import app

client = TestClient(app)

# POST /menu

def test_create_beverage_success():
    response = client.post("/menu", json={"name": "Latte", "size": "M", "price": 3.5})
    assert response.status_code == 201
    assert response.json() == {"message": "Bebida agregada exitosamente."}

def test_create_beverage_invalid_name():
    response = client.post("/menu", json={"name": "", "size": "L", "price": 2.0})
    assert response.status_code == 400
    assert "Nombre" in response.json()["detail"]

def test_create_beverage_negative_price():
    response = client.post("/menu", json={"name": "Espresso", "size": "S", "price": -1})
    assert response.status_code == 400
    assert "precio" in response.json()["detail"]

# GET /menu

def test_get_menu_success():
    response = client.get("/menu")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

# GET /menu/{name}

def test_get_beverage_by_name_success():
    client.post("/menu", json={"name": "Americano", "size": "L", "price": 2.5})
    response = client.get("/menu/Americano")
    assert response.status_code == 200
    assert response.json()["name"] == "Americano"

def test_get_beverage_by_name_not_found():
    response = client.get("/menu/NoExiste")
    assert response.status_code == 404
    assert response.json()["detail"] == "Bebida no encontrada"