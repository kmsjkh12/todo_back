import React, { useState } from "react";
interface InputProps {
    content: string
    onClick: React.MouseEventHandler<HTMLButtonElement>;
    onChange :React.ChangeEventHandler <HTMLInputElement>
}
const InputForm = (props: InputProps) =>{
    const { onClick, onChange, content } = props;
    
    return(
        <div>
            <input type="text" onChange={onChange} value={content} />
            <button type="button"  onClick={onClick}>추가하기</button>
        </div>
    )
}
export default InputForm;
