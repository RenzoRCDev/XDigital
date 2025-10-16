import json, pyodbc

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])

        id_pedido = body['id_pedido']
        id_producto = body['id_producto']
        cantidad = body['cantidad']
        precio_unitario = body['precio_unitario']

        # Parámetros de conexión
        driver = '{ODBC Driver 17 for SQL Server}'
        server = 'database-1.clu6gy7vcjed.us-east-1.rds.amazonaws.com'
        database = 'XDigital'
        username = 'admin'
        password = 'moviles2025.'

        with pyodbc.connect(f"Driver={driver};Server={server};Database={database};UID={username};PWD={password};") as conn:
            with conn.cursor() as cursor:
                cursor.execute(
                    "EXEC sp_AgregarDetallePedido ?, ?, ?, ?;",
                    (id_pedido, id_producto, cantidad, precio_unitario)
                )
                conn.commit()

                return {
                    'statusCode': 201,
                    'body': json.dumps({
                        'Mensaje': 'Detalle de pedido agregado correctamente',
                        'Id_Pedido': id_pedido
                    })
                }

    except Exception as e:
        return {'Error': str(e)}