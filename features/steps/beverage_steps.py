import requests
from behave import given, when, then

API_URL = "http://localhost:8000/menu"
headers = {"Content-Type": "application/json"}

@given("el menú está vacío")
def step_clear_menu(context):
    context.menu = []
    # Como no hay endpoint para limpiar, solo lo anotamos en contexto.

@when('agrego la bebida "{name}" de tamaño "{size}" con precio {price}')
def step_add_beverage(context, name, size, price):
    payload = {
        "name": name,
        "size": size,
        "price": float(price)
    }
    context.response = requests.post(API_URL, json=payload, headers=headers)

@when('intento agregar una bebida sin nombre con tamaño "{size}" y precio {price}')
def step_add_beverage_no_name(context, size, price):
    payload = {
        "name": "",
        "size": size,
        "price": float(price)
    }
    context.response = requests.post(API_URL, json=payload, headers=headers)

@then("el sistema debe devolver un mensaje de éxito")
def step_check_success(context):
    assert context.response.status_code == 201
    assert "Bebida agregada exitosamente" in context.response.json()["message"]

@then("el sistema debe responder con error 400")
def step_check_bad_request(context):
    assert context.response.status_code == 400
