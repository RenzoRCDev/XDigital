import json, pyodbc

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])

        nombre = body['nombre']
        descripcion = body['descripcion']
        precio = body['precio']
        stock = body['stock']
        id_categoria = body['id_categoria']
        id_marca = body['id_marca']

        driver = '{ODBC Driver 17 for SQL Server}'
        server = 'database-1.clu6gy7vcjed.us-east-1.rds.amazonaws.com'
        database = 'XDigital'
        username = 'admin'
        password = 'moviles2025.'

        with pyodbc.connect(f"Driver={driver};Server={server};Database={database};UID={username};PWD={password};") as conn:
            with conn.cursor() as cursor:
                cursor.execute("""
                    DECLARE @nuevo_id INT;
                    EXEC sp_InsertarProducto ?, ?, ?, ?, ?, ?, @nuevo_id OUTPUT;
                    SELECT @nuevo_id;
                """, (nombre, descripcion, precio, stock, id_categoria, id_marca))

                row = cursor.fetchone()
                conn.commit()

                return {
                    'statusCode': 201,
                    'body': json.dumps({
                        'Mensaje': 'Producto insertado correctamente',
                        'Nuevo_Id': row[0]
                    })
                }

    except Exception as e:
        return {'Error': str(e)}

