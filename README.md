# RoadAnalyzer
application for cars
## Tables db

Unique id numbering of all tables with UUID.randomUUID()

**Part_and_Service**
| id | description                                     | type |
|----|-------------------------------------------------|------|
| 1  | Автобензин АИ-95                                | 1    |
| 2  | Мойка машины                                    | 2    |
| 3  | Тормозные колодки передние комплект TRW GDB2108 | 3    |

**Expense_Type**
| id | description |
|----|-------------|
| 1  | топливо     |
| 2  | услуги      |
| 3  | запчасти    |

**Spending**
| id | date       | Part_and_Service_ID | description     | count | amount  |
|----|------------|---------------------|-----------------|-------|---------|
| 1  | 16.06.2024 | 1                   |                 | 30    | 1615.15 |
| 2  | 20.06.2024 | 2                   | кузов + коврики | 1     | 100     |
| 3  | 25.06.2024 | 3                   |                 | 1     | 2400    |

**Part_group**
| id | description       |
|----|-------------------|
| 1  | тормозная система |
| 2  | обшивка двери     |
| 3  | кондиционер       |
| 4  | инструменты       |
| 5  | расходники        |
| 6  | запчасти б/у      |

**Info**
| id | key                | value      |
|----|--------------------|------------|
| 1  | дата выпуска:      | 25.01.2023 |
| 2  | стоимость покупки: | 600000     |
| 3  | марка авто         | Skoda      |

---
automatically generated table by triggers (AFTER INSERT, AFTER UPDATE, AFTER DELETE).

**Fuel**
| id | Spending_ID | date       | fuel_description | count | price | amount  | odometer | description |
|----|-------------|------------|------------------|-------|-------|---------|----------|-------------|
| 1  | 1           | 16.06.2024 | Автобензин АИ-95 | 30    | 55.45 | 1663.50 | 137014   |             |
| 2  | 8           | 13.07.2024 | Автобензин АИ-95 | 26    | 55.60 | 1445.60 |          | трасса      |
| 3  | 30          | 25.07.2024 | Автобензин АИ-95 | 35    | 52.85 | 1849.75 | 138775   | Алтай       |

**Parts Stock**
| id | Spending_ID | date       | part_description                                                     | description  | OEM       | count | price  | amount | status    | part_group_id |
|----|-------------|------------|----------------------------------------------------------------------|--------------|-----------|-------|--------|--------|-----------|---------------|
| 1  | 3           | 25.06.2024 | Тормозные колодки передние комплект TRW GDB2108                      |              | GDB2108   | 1     | 2400   | 2400   | ordered   | 1             |
| 2  | 13          | 07.07.2024 | Винт со скр.цил.гол.,внут.TORX M6X16 креп. двер. карты VAG N91096701 | аналог ориг. | N91096701 | 5     | 50     | 250    | available | 2             |
| 3  | 23          | 15.07.2024 | Кольцо уплотнительное трубки кондиционера 14,3х2,4 VAG 7H0820898     |              | 7H0820898 | 3     | 220.01 | 660.03 | installed | 3             |

---
temporary table

**Vehicle_System**
| id | Part_and_Service_ID | part_group_id | description                    | count | price | amount | available |
|----|---------------------|---------------|--------------------------------|-------|-------|--------|-----------|
| 1  | 3                   | 1             | менять вместе с медной смазкой | 1     | 2400  | 2400   | false     |
| 2  | 23                  | 7             |                                | 5     | 70    | 350    | true      |
| 3  | 38                  | 8             | + смазка                       | 1     | 500   | 500    | false     |

## API

---
Spendings
1. Create Spending  
HTTP: POST  
URL: /api/spending  
  ```json
  {
    "date": "2024-06-16",
    "Part_and_Service_ID": 1,
    "description": "",
    "count": 30,
    "amount": 1615.15
  }
```

2. Get All Spending  
HTTP: GET  
URL: /api/spending  
  ```json
  [
    {
      "id": 1,
      "date": "2024-06-16",
      "Part_and_Service_ID": 1,
      "description": "",
      "count": 30,
      "amount": 1615.15
    },
    {
      "id": 2,
      "date": "2024-06-20",
      "Part_and_Service_ID": 2,
      "description": "кузов + коврики",
      "count": 1,
      "amount": 100
    }
  ]
  ```

3. Get Spending by ID  
HTTP: GET  
URL: /api/spending/{id}  
  ```json
  {
    "id": 1,
    "date": "2024-06-16",
    "Part_and_Service_ID": 1,
    "description": "",
    "count": 30,
    "amount": 1615.15
  }
```

4. Update Spending  
HTTP: PUT  
URL: /api/spending/{id}  
  ```json
  {
    "date": "2024-06-16",
    "Part_and_Service_ID": 1,
    "description": "Обновленное описание",
    "count": 30,
    "amount": 1700.00
  }
  ```

5. Delete Spending  
HTTP: DELETE  
URL: /api/spending/{id}  
