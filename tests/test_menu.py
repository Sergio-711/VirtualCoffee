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
    assert "nombre" in response.json()["detail"]

def test_create_beverage_negative_price():
    response = client.post("/menu", json={"name": "Espresso", "size": "S", "price": -1})
    assert response.status_code == 400
    assert "precio" in response.json()["detail"]

def test_create_beverage_internal_error(monkeypatch):
    def mock_create(*args, **kwargs):
        raise Exception("Simulated failure")
    monkeypatch.setattr("app.create_beverage", mock_create)
    response = client.post("/menu", json={"name": "Mocha", "size": "M", "price": 4.0})
    assert response.status_code == 500

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

def test_get_beverage_internal_error(monkeypatch):
    def mock_get(*args, **kwargs):
        raise Exception("Oops")
    monkeypatch.setattr("app.get_beverage_by_name", mock_get)
    response = client.get("/menu/Latte")
    assert response.status_code == 500
