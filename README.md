![스크린샷 2024-10-21 오전 7 45 07](https://github.com/user-attachments/assets/49571aec-9589-4f56-94de-7f094e2e4064)
![스크린샷 2024-10-21 오전 7 45 12](https://github.com/user-attachments/assets/077fa767-7222-455e-a28d-563d21ac95c6)

LazyColumn은 RecyclerView와 동일하다.
LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
    items(items = names) { name ->
        Greeting(name = name)
    }  
}

remember, rememberSaveable(화면 회전 시에도 유지된다), mutableStateOf, 
함수 간 클릭 이벤트 넘기기

애니메이션
val extraPadding by animateDpAsState(
    if(expanded) 48.dp else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)
