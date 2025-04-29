from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List

class Beverage(BaseModel):
    name: str
    size: str
    price: float

menu: list[Beverage] = []
app = FastAPI()

@app.get("/menu", response_model=List[Beverage])
def get_menu():
    return menu

@app.post("/menu", status_code=201)
def create_beverage(beverage: Beverage):
    if not beverage.name:
        raise HTTPException(status_code=400, detail="Nombre de la bebida es requerido")
    if beverage.price <= 0:
        raise HTTPException(status_code=400, detail="El precio debe ser mayor a cero")
    menu.append(beverage)
    return {"message": "Bebida agregada exitosamente."}

@app.get("/menu/{name}", response_model=Beverage)
def get_beverage_by_name(name: str):
    for beverage in menu:
        if beverage.name.lower() == name.lower():
            return beverage
    raise HTTPException(status_code=404, detail="Bebida no encontrada")